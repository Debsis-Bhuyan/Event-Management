import React from "react";
import image from "../assets/clap.jpg";
import RegistrationForm from "./RegisterForm";

const Auth = () => {
  return (
    <div className="relative h-auto auth py-10 min-h-screen px-[4px]">
      <div className="max-w-[90rem] mx-auto bg-white h-auto lg:h-[600px] rounded-[20px] py-5 mt-20 lg:ps-10">
        <div className="flex justify-around items-center h-full">
          <div className="relative rounded-[20px] w-[45%] h-full hidden lg:block">
            <img
              src={image}
              alt=""
              className="rounded-[20px] w-full h-full object-cover object-bottom"
            />

            <div
              className=" rounded-[20px] h-full  w-full  items-center text-center flex justify-center absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 px-4
                             py-2 bg-[rgba(0,0,0,0.3)] text-white font-bold"
            >
              <p className="text-center text-2xl font-bold">Join the Events </p>
            </div>
          </div>

          <RegistrationForm />
        </div>
      </div>
    </div>
  );
};

export default Auth;
