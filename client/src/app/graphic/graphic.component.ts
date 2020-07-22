import { Component, OnInit, Output, Input } from '@angular/core';
import { ChartDataSets, ChartOptions } from 'chart.js';
import { Color, Label } from 'ng2-charts';
@Component({
  selector: 'app-graphic',
  templateUrl: './graphic.component.html',
  styleUrls: ['./graphic.component.scss']
})
export class GraphicComponent implements OnInit {
  lineChartData: ChartDataSets[] = [
    { data: [], label: 'CQRS' },
    { data: [], label: 'CRUD' },
  ];

  lineChartLabels: Label[] = [];

  lineChartOptions = {
    responsive: true,
  };

  lineChartColors: Color[] = [
    {
      borderColor: 'black',
      backgroundColor: 'rgba(255,255,0,0.28)',
    },
  ];

  lineChartLegend = true;
  lineChartPlugins = [];
  lineChartType = 'line';
  
  @Input() title:string
  @Input() set cqrs(value: any[]) {
    let list = this.lineChartData[0].data
    this.populateList(list,value)
  }
  @Input() set crud(value: any[]) {
    let list = this.lineChartData[1].data
    this.populateList(list,value)
  }

  constructor() { }

  ngOnInit(): void {
  }

  private populateList (list,value):void{
  
    for (let i = list.length; i < value.length; i++) {
      const element = value[i];
      list.push(element);
    }

    this.updateLabel(list.length)
  }
  private updateLabel (size):void{
      if(size<this.lineChartLabels.length){
        return
      }
      for (let i=this.lineChartLabels.length; i<size; i++){
        this.lineChartLabels.push(i.toString())
      }
  }
  
}
