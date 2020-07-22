import { Component, OnInit, Input } from '@angular/core';
import { ApiService } from '../services/api.service' 
import { interval, Subscription } from 'rxjs';

@Component({
  selector: 'app-tester',
  templateUrl: './tester.component.html',
  styleUrls: ['./tester.component.scss']
})
export class TesterComponent implements OnInit {

  @Input() mode:string;
  @Input() crud:string;
  @Input() cqrs:string;

  readonly MAX_ITERATIONS:number = 50;
  cqrsData=[];
  crudData=[];
  constructor(private apiService: ApiService) {   }

  ngOnInit(): void {    
    let action = (this.mode == "READ" ? this.apiService.get : this.apiService.post).bind(this.apiService)
    this.loopaction(action, this.cqrs,(list:any[])=>{
      this.cqrsData = list
    })
    this.loopaction(action, this.crud,(list:any[])=>{
      this.crudData = list
    })
  }

  loopaction (action:Function, endpoint: string, callback:Function, list:any[] = []):void{
    let t1 = Date.now()
    action(endpoint).subscribe((timespent: any) => {
      const clonedArray  = [...list, timespent];
      callback(clonedArray)
      if(list.length>=this.MAX_ITERATIONS){
        return
      };

      setTimeout(()=>{
        this.loopaction(action, endpoint, callback, clonedArray);
      },Math.max(2000 - (Date.now()-t1),300))//Not too fast, not too slow...
    })
  }
}
