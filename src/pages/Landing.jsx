import React, { useEffect } from "react";
import Hero from "../components/Hero";
import Benefits from "../components/Benefits";
import Layout from "./Layout";
import Slider from "../components/Slider";
import { useDispatch, useSelector } from "react-redux";
import axios from "axios";
import { APP_URL } from "../utils";
import { setEvent } from "../store/eventSlice";

// ok

const Landing = () => {
 
  return (
    <Layout>
      <Hero />
      <div className="bg-[#001e2b] relative z-50 pt-10 pb-5 h-auto">
        <Benefits />
        <Slider />
      </div>
    </Layout>
  );
};

export default Landing;
