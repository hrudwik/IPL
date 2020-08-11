import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PredictFormModalComponent } from './predict-form-modal.component';

describe('PredictFormModalComponent', () => {
  let component: PredictFormModalComponent;
  let fixture: ComponentFixture<PredictFormModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PredictFormModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PredictFormModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
