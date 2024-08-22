import axios from "axios";
import React, { useEffect } from "react";
import { APP_URL } from "../utils";
import { useDispatch } from "react-redux";
import { setUser } from "../store/userSlice";
import { useNavigate } from "react-router-dom";

const QueryParam = () => {
  const params = new URLSearchParams(document.location.search);
  const status = params.get("status");
  const username = params.get("userName");
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const formData = {
    email: username,
    password: "deba120",
  };

  const getUserData = async () => {
    if (status == "success") {
      console.log(formData);
      try {
        const response = await axios.post(`${APP_URL}/auth/login`, formData, {
          withCredentials: true,
        });
        console.log(response.data);
        if (response.data) {
          dispatch(setUser(response.data));
          setTimeout(() => {
            navigate("/dashboard");
          }, 1000);
        }
      } catch (error) {
        setError("Something went wrong! Please try again.");
      } finally {
      }
    }
  };

  getUserData();

  return <div>QueryParam</div>;
};

export default QueryParam;
