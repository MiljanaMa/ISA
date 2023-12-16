import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LayoutModule } from './layout/layout.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './infrastructure/material/material.module';
import { CompanyModule } from './feature-modules/company/company.module';
import { FullCalendarModule } from '@fullcalendar/angular';
import { AuthModule } from './auth/auth.module';
import { TokenInterceptor } from './auth/interceptor/token-interceptor';
import { ClientModule } from './client/client.module';
import { SystemAdminModule } from './system-admin/system-admin.module';


@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    LayoutModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MaterialModule, 
    CompanyModule,
    AuthModule,
    ClientModule,
    SystemAdminModule,
    
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
