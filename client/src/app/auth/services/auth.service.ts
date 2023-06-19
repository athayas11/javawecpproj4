import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { environment } from "src/environments/environment";
import { map, Observable } from "rxjs";
import { User } from "../types/user";

@Injectable({
  providedIn: "root",
})
export class AuthService {
  private loginUrl = `${environment.apiUrl}`;

  constructor(private http: HttpClient) {}

  login(user: User): Observable<{ [key: string]: string }> {
    // @todo : Api call to /authenticate
  }

  getToken() {
    return localStorage.getItem("token");
  }

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.loginUrl}/users`);
  }

  createUser(user: User): Observable<User> {
    // @todo : should create a user in the system
  }
}
