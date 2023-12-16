import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompanyProfileAdminComponent } from './company-profile-admin.component';

describe('CompanyProfileAdminComponent', () => {
  let component: CompanyProfileAdminComponent;
  let fixture: ComponentFixture<CompanyProfileAdminComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CompanyProfileAdminComponent]
    });
    fixture = TestBed.createComponent(CompanyProfileAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
