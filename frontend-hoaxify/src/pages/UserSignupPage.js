import React from "react";
import { singUp } from "./../api/apiCalls";
import Input from "./../components/Input";
class UserSignupPage extends React.Component {
  state = {
    userName: null,
    displayName: null,
    password: null,
    repeatPassword: null,
    pendingApiCall: false,
    errors: {},
  };

  onChange = (event) => {
    const { name, value } = event.target;
    const errors = { ...this.state.errors };
    errors[name] = undefined;
    if (name === "password" || name === "passwordRepeat") {
      if (name === "password" && value !== this.state.passwordRepeat) {
        errors["passwordRepeat"] = "Password mismatch";
      } else if (name === "passwordRepeat" && value !== this.state.password) {
        errors["passwordRepeat"] = "Password mismatch";
      } else {
        errors["passwordRepeat"] = undefined;
      }
    }
    this.setState({
      [name]: value,
      errors,
    });
  };

  OnClickSignUp = async (event) => {
    event.preventDefault();
    const { userName, displayName, password } = this.state;
    this.setState({ pendingApiCall: true });
    const userRequestModel = {
      userName,
      displayName,
      password,
    };

    try {
      const response = await singUp(userRequestModel);
      console.log("response", response);
    } catch (error) {
      if (error.response.data.validationErrors) {
        this.setState({
          errors: error.response.data.validationErrors,
        });
      }
    }
    this.setState({ pendingApiCall: false });
  };

  render() {
    const { pendingApiCall, errors } = this.state;
    const { userName, displayName, password, passwordRepeat } = errors;
    debugger;
    return (
      <div className="container">
        <form>
          <h1 className="text-center">Sign Up</h1>
          <Input
            label="User Name"
            name="userName"
            onChange={this.onChange}
            error={userName}
            type="text"
          ></Input>
          <Input
            label="Display Name:"
            name="displayName"
            onChange={this.onChange}
            error={displayName}
            type="text"
          ></Input>
          <Input
            label="Password:"
            name="password"
            onChange={this.onChange}
            error={password}
            type="password"
          ></Input>
          <Input
            label="Repeat Password:"
            name="passwordRepeat"
            onChange={this.onChange}
            error={passwordRepeat}
            type="password"
          ></Input>
          <button
            disabled={pendingApiCall || passwordRepeat !== undefined}
            className="btn btn-primary"
            onClick={this.OnClickSignUp}
          >
            {pendingApiCall && (
              <span
                className="spinner-border spinner-border-sm"
                role="status"
                aria-hidden="true"
              ></span>
            )}
            Sign Up
          </button>
        </form>
      </div>
    );
  }
}

export default UserSignupPage;
