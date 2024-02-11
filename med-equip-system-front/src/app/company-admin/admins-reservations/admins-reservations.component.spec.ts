import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminsReservationsComponent } from './admins-reservations.component';

describe('AdminsReservationsComponent', () => {
  let component: AdminsReservationsComponent;
  let fixture: ComponentFixture<AdminsReservationsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminsReservationsComponent]
    });
    fixture = TestBed.createComponent(AdminsReservationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
