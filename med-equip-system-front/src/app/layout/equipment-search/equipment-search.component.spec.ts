import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EquipmentSearchComponent } from './equipment-search.component';

describe('EquipmentSearchComponent', () => {
  let component: EquipmentSearchComponent;
  let fixture: ComponentFixture<EquipmentSearchComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EquipmentSearchComponent]
    });
    fixture = TestBed.createComponent(EquipmentSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
