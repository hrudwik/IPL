import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { RegistrationService } from '../registration.service';

@Component({
  selector: 'app-predict-form-modal',
  templateUrl: './predict-form-modal.component.html',
  styleUrls: ['./predict-form-modal.component.css']
})
export class PredictFormModalComponent implements OnInit {

  @Input()id: number;
  myForm: FormGroup;

  constructor(private _service: RegistrationService, public activeModal: NgbActiveModal,
    private formBuilder: FormBuilder) {
      this.matchDetails();
  }

  private createForm() {
    this.myForm = this.formBuilder.group({
      username: '',
      password: '',
      mom: ''
    });
  }

  public submitForm() {
    this.activeModal.close(this.myForm.value);
  }

  matchDetails() {
    this.createForm();
    this._service.matchDetailsFromRemote("1").subscribe(
      data => {
        console.log("dhc response received");
        console.log(data);
      },
      error => {
        console.log("dhc exception occured");
      }
    )
  }


  ngOnInit(): void {
  }

  closeModal() {
    this.activeModal.close('Modal Closed');
  }

}
