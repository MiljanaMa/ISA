import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompanyWorkCalendarComponent } from './company-work-calendar.component';

describe('CompanyWorkCalendarComponent', () => {
  let component: CompanyWorkCalendarComponent;
  let fixture: ComponentFixture<CompanyWorkCalendarComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CompanyWorkCalendarComponent]
    });
    fixture = TestBed.createComponent(CompanyWorkCalendarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
