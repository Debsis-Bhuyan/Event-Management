import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import logo from "../assets/logo-sm.png";
import google from "../assets/google.png";
import facebook from "../assets/FacebookImage.webp";
import axios from "axios";
import { APP_URL } from "../utils";
import { useDispatch } from "react-redux";
import { setUser } from "../store/userSlice";


const RegistrationForm = () => {
  const dispatch =  useDispatch()
  const navigate = useNavigate()
  const [formData, setFormData] = useState({
    fullName: "",
    email: "",
    password: "",
    confirmPassword: "",
  });
  const [loading, setLoading] = useState(false);
  const [errorMsg, setErrorMessage] = useState("");

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setErrorMessage("");

    if (formData.password !== formData.confirmPassword) {
      setErrorMessage("Passwords do not match!");
      return;
    }

    setLoading(true);
    try {
      const response = await axios.post(`${APP_URL}/auth/signup`, {
        fullName: formData.fullName,
        email: formData.email,
        password: formData.password,
      });
      if (response.data) {
        alert(response.data?.message);
        dispatch(setUser(response.data));
        console.log(response.data);
        navigate("/dashboard")
      }
      
      
    } catch (error) {
      setErrorMessage(error.message)
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="h-auto lg:h-full px-4 w-full lg:w-[45%] flex flex-col items-center overflow-y-auto no-scrollbar">
      <div className="flex flex-col justify-center items-center">
        <div>
          <img src={logo} alt="Logo" className="h-11 object-cover" />
        </div>
        <h2 className="font-bold text-2xl">
          Welcome to Event Management System
        </h2>
        <p className="my-1 text-sm text-gray-700">
          Enter the details below to create an account
        </p>
      </div>
      <form onSubmit={handleSubmit} className="w-full">
        <div className="mb-4">
          <label htmlFor="fullName" className="block text-gray-700">
            Name
          </label>
          <input
            type="text"
            name="fullName"
            value={formData.fullName}
            onChange={handleChange}
            placeholder="Your full name"
            className="w-full h-[40px] border border-gray-300 rounded-md px-2"
            required
          />
        </div>

        <div className="mb-4">
          <label htmlFor="email" className="block text-gray-700">
            Email
          </label>
          <input
            type="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
            placeholder="Your email"
            className="w-full h-[40px] border border-gray-300 rounded-md px-2"
            required
          />
        </div>

        <div className="mb-4">
          <label htmlFor="password" className="block text-gray-700">
            Password
          </label>
          <input
            type="password"
            name="password"
            value={formData.password}
            onChange={handleChange}
            placeholder="Your password"
            className="w-full h-[40px] border border-gray-300 rounded-md px-2"
            required
          />
        </div>

        <div className="mb-4">
          <label htmlFor="confirmPassword" className="block text-gray-700">
            Confirm Password
          </label>
          <input
            type="password"
            name="confirmPassword"
            value={formData.confirmPassword}
            onChange={handleChange}
            placeholder="Confirm your password"
            className="w-full h-[40px] border border-gray-300 rounded-md px-2"
            required
          />
        </div>
       

        <div className="mb-4">
          <button
            type="submit"
            className="w-full h-[40px] bg-black text-white rounded-md hover:bg-gray-900"
            disabled={loading}
          >
            {loading ? "Registering..." : "Register"}
          </button>
        </div>

        {errorMsg && <p className="text-red-500 text-center">{errorMsg}</p>}

        <div>
          <button
            type="button"
            className="w-full inline-flex items-center justify-center mb-5 py-2 px-4 border border-gray-300 shadow-sm text-base font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50"
          >
            <img src={google} alt="Google" className="h-6 mr-2" />
            Sign in with Google
          </button>
          <button
            type="button"
            className="w-full inline-flex items-center justify-center mb-5 py-2 px-4 border border-gray-300 shadow-sm text-base font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50"
          >
            <img src={facebook} alt="Facebook" className="h-8 mr-2" />
            Sign in with Facebook
          </button>
          Already have an account?
          <Link
            to="/login"
            className="font-medium text-indigo-600 hover:text-indigo-500 text-center mt-2 ml-2"
          >
            Login here
          </Link>
        </div>
      </form>
    </div>
  );
};

export default RegistrationForm;
