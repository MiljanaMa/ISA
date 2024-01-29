import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SimulatorMapComponent } from './simulator-map.component';

describe('SimulatorMapComponent', () => {
  let component: SimulatorMapComponent;
  let fixture: ComponentFixture<SimulatorMapComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SimulatorMapComponent]
    });
    fixture = TestBed.createComponent(SimulatorMapComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
