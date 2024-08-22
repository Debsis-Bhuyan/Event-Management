import React from "react";
import dash from "../assets/dash.png";
import { useNavigate } from "react-router-dom";
import { heros } from "../data/links";
import { FaChevronRight } from "react-icons/fa6";

const Benefits = () => {
  const navigate = useNavigate();
  return (
    <div className="max-w-[90rem] mx-auto py-4 border border-gray-700 px-4 flex flex-col">
      <div className="flex flex-wrap lg:flex-nowrap mt-4 py-5">
        <div className="w-full">
          <p className="text-white mt-2 mb-8 text-2xl font-bold lg:pr-[3rem] w-full">
            “Built for event enthusiasts, by event enthusiasts. Event Management
            System - your go-to platform for seamless event management.”
          </p>
          <p className="text-white">
            Welcome to Event Management system – Your Ultimate Event Management
            Solution! From seamless planning to flawless execution, we've got
            you covered. Elevate your events with our powerful tools and expert
            support. Sign up now to experience the successful events!
          </p>

          <button
            className="mt-10 mb-10 text-white bg-primary border px-5 py-3 rounded font-semibold transition duration-300 ease-in-out hover:bg-red-600"
            onClick={() => navigate("/register")}
          >
            Join us today
          </button>
        </div>
      </div>

      <div className="flex flex-wrap gap-7 w-full px-4 justify-center md:justify-normal lg:justify-center items-center my-10">
        {heros.map((item) => (
          <div
            key={item.name}
            className="bg-[#001e2b] text-white rounded-[8px] w-full max-w-[300px] p-4 transition-transform duration-300 ease-in-out transform hover:scale-105 hover:bg-[#061621] border"
          >
            <div className="flex flex-col gap-2">
              <div>
                <item.icon
                  size="40"
                  className="bg-[#001e2b] p-2 rounded-full border"
                />
              </div>
              <div>
                <h2 className="text-xl font-semibold">{item.name}</h2>
              </div>
              <div>
                <p className="text-sm line-clamp-4">{item.message}</p>
              </div>
            </div>
            <div className="mt-2">
              <button className="flex font-bold items-center text-primary">
                View all events <FaChevronRight className="ml-2" />
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Benefits;
