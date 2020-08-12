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

  teams: String[] = []
  players: String[] = []

  @Input() public id: String;
  myForm: FormGroup;

  constructor(private _service: RegistrationService, public activeModal: NgbActiveModal,
    private formBuilder: FormBuilder) {
      this.createForm();
  }

  private createForm() {
    this.myForm = this.formBuilder.group({
      winner:  [null, [ Validators.required ] ],
      mom:  [null, [ Validators.required ] ],
      bestbatsmen:  [null, [ Validators.required ] ],
      bestbowler:  [null, [ Validators.required ] ]
    });
  }

  public submitForm() {
    this.activeModal.close(this.myForm.value);
  }

  matchDetails() {
    this._service.nextThreeMatchDetailsFromRemote(this.id).subscribe(
      data => {
        console.log("dhc response received");
        console.log(data);
        this.players = data.players;
        this.teams.push(data.teamName1);
        this.teams.push(data.teamName2);
        console.log("teams");
        console.log(this.teams);
      },
      error => {
        console.log("dhc exception occured");
      }
    )
  }


  ngOnInit(): void {
    this.matchDetails();
  }

  closeModal() {
    this.activeModal.close('Modal Closed');
  }

}
