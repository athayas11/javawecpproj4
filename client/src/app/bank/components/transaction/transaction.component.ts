import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Observable, map, of } from "rxjs";
import { AuthService } from "src/app/auth/services/auth.service";
import { User } from "src/app/auth/types/user";
import { TransactionService } from "../../services/transaction.service";
import { Transaction } from "../../types/transaction";

@Component({
  selector: "app-transaction",
  templateUrl: "./transaction.component.html",
  styleUrls: ["./transaction.component.scss"],
})
export class TransactionComponent implements OnInit {
  transactionForm: FormGroup;

  transactionError$: Observable<string>;
  transactionSuccess$: Observable<string>;
  users$: Observable<User[]>;
  isFormSubmitted: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private trnasactionService: TransactionService
  ) {}

  ngOnInit(): void {
    this.transactionForm = this.formBuilder.group({
      user: ["", [Validators.required]],
      amount: ["", ""],
      type: ["", ""],
    });
 
  }

  onSubmit() {
    this.isFormSubmitted = true;
    //@todo:  when a trasaction is submitted make sure relevant fields are not empty, and make an api call to perform transaction
  }
}
