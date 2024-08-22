import axios from "axios";
import { useDispatch } from "react-redux";
export const APP_URL = "http://localhost:9090/api";

export const getEvents = async () => {
    
  try {
    const response = await axios.get(`${APP_URL}/event/get-all`);
    console.log(response.data)
    return await response.data;
  } catch (error) {
    console.log(error)
    return null;
  }
};
