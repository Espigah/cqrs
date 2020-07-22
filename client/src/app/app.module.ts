import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { GraphicComponent } from './graphic/graphic.component';
import { HttpClientModule } from '@angular/common/http';

import { ChartsModule } from 'ng2-charts';
import { TesterComponent } from './tester/tester.component';

@NgModule({
  declarations: [
    AppComponent,
    GraphicComponent,
    TesterComponent
  ],
  imports: [
    BrowserModule,
    ChartsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
