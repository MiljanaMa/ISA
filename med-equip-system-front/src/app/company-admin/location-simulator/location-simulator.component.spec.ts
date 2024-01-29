import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LocationSimulatorComponent } from './location-simulator.component';

describe('LocationSimulatorComponent', () => {
  let component: LocationSimulatorComponent;
  let fixture: ComponentFixture<LocationSimulatorComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LocationSimulatorComponent]
    });
    fixture = TestBed.createComponent(LocationSimulatorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
