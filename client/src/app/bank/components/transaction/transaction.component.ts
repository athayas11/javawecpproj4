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
    this.users$ = this.authService
      .getUsers()
      .pipe(map((users) => users.filter((u) => u.role === "CUSTOMER")));
  }

  onSubmit() {
    this.isFormSubmitted = true;
    if (this.transactionForm.invalid) {
      return;
    } else {
      const { user, amount, type } = this.transactionForm.value;
      const transaction: Transaction = {
        userId: user,
        transactionAmount: amount,
        transactionType: type,
      };
      this.trnasactionService.performTransaction(transaction).subscribe(
        (res: any) => {
          this.transactionSuccess$ = of(res.success);
        },
        (error) => {
          this.transactionError$ = of("Error in performing transaction");
        }
      );
    }
  }
}
