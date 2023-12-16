import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompanyAdminCreationComponent } from './company-admin-creation.component';

describe('CompanyAdminCreationComponent', () => {
  let component: CompanyAdminCreationComponent;
  let fixture: ComponentFixture<CompanyAdminCreationComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CompanyAdminCreationComponent]
    });
    fixture = TestBed.createComponent(CompanyAdminCreationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
