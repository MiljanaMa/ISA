import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SystemAdminCreationComponent } from './system-admin-creation.component';

describe('SystemAdminCreationComponent', () => {
  let component: SystemAdminCreationComponent;
  let fixture: ComponentFixture<SystemAdminCreationComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SystemAdminCreationComponent]
    });
    fixture = TestBed.createComponent(SystemAdminCreationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
