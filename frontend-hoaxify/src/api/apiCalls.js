import axios from "axios";

export const singUp = (body) => {
  return axios.post("/api/v1/users/create-user", body);
};
