import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-predict-form-modal',
  templateUrl: './predict-form-modal.component.html',
  styleUrls: ['./predict-form-modal.component.css']
})
export class PredictFormModalComponent implements OnInit {

  constructor(public activeModal: NgbActiveModal) { }

  ngOnInit(): void {
  }

  closeModal() {
    this.activeModal.close('Modal Closed');
  }

}
