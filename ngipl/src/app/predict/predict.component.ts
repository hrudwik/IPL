import { Component, OnInit } from '@angular/core';

import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';
import { PredictFormModalComponent } from '../predict-form-modal/predict-form-modal.component';

@Component({
  selector: 'app-predict',
  templateUrl: './predict.component.html',
  styleUrls: ['./predict.component.css']
})
export class PredictComponent implements OnInit {
  closeResult = '';

  constructor(private modalService: NgbModal) { }

  ngOnInit(): void {
  }

  openFormModalFirst() {
    const modalRef = this.modalService.open(PredictFormModalComponent);
    modalRef.componentInstance.id = "1"; // should be the id
    
    modalRef.result.then((result) => {
      console.log(result);
    }).catch((error) => {
      console.log(error);
    });
  }

  openFormModalSecond() {
    const modalRef = this.modalService.open(PredictFormModalComponent);
    modalRef.componentInstance.id = "2"; // should be the id
    
    modalRef.result.then((result) => {
      console.log(result);
    }).catch((error) => {
      console.log(error);
    });
  }

  openFormModalThird() {
    const modalRef = this.modalService.open(PredictFormModalComponent);
    modalRef.componentInstance.id = "3"; // should be the id
    
    modalRef.result.then((result) => {
      console.log(result);
    }).catch((error) => {
      console.log(error);
    });
  }

}
