import React, { useState } from "react";
import axios from "axios";
import { useSelector } from "react-redux";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { APP_URL } from "../utils/index.js";

const EventCreationForm = () => {
  const userData = useSelector((state) => state.user.user);
  const [formData, setFormData] = useState({
    title: "",
    description: "",
    startTime: null,
    endTime: null,
    location: "",
  });
  const [isLoading, setIsLoading] = useState(false);
  const [errorMsg, setErrorMessage] = useState("");
  const [infoMsg, setInfoMessage] = useState("");

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleDateChange = (date, field) => {
    setFormData({
      ...formData,
      [field]: date,
    });
  };
console.log(userData)
  const onSubmit = async (e) => {
    e.preventDefault();
    setErrorMessage("");
    setInfoMessage("");
    setIsLoading(true);

    const payload = {
      ...formData,
      user_id: userData.id,
      start_date: formData.startDate,
      end_date: formData.endDate,
    };
    // private String title;
    // private  String description;
    // private String location;
    // private LocalDateTime startTime;
    // private  LocalDateTime endTime;
    // private  Double ticketPrice;
    // private Integer capacity;
    const userId = userData
    try {
      const response = await axios.post(
        `${APP_URL}/event/create-event/${userId}`,
        payload
      );
      setInfoMessage("Event successfully created");
      setFormData({
        title: "",
        description: "",
        startDate: null,
        endDate: null,
        venueId: "",
      });
    } catch (error) {
      setErrorMessage(error.response?.data?.error || "Something went wrong!");
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="px-4 max-w-lg mx-auto">
      <form onSubmit={onSubmit} className="space-y-4">
        <div>
          <label htmlFor="title" className="block text-sm font-medium">
            Event Title
          </label>
          <input
            type="text"
            id="title"
            name="title"
            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm p-2"
            value={formData.title}
            onChange={handleInputChange}
            required
          />
        </div>
        <div>
          <label htmlFor="description" className="block text-sm font-medium">
            Description
          </label>
          <textarea
            id="description"
            name="description"
            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm p-2"
            rows="4"
            value={formData.description}
            onChange={handleInputChange}
            required
          />
        </div>
        <div>
          <label className="block text-sm font-medium">Dates</label>
          <div className="flex space-x-2">
            <DatePicker
              selected={formData.startDate}
              onChange={(date) => handleDateChange(date, "startDate")}
              selectsStart
              startDate={formData.startDate}
              endDate={formData.endDate}
              className="w-full border border-gray-300 rounded-md shadow-sm p-2"
              placeholderText="Start Date"
              showTimeSelect
            />
            <DatePicker
              selected={formData.endDate}
              onChange={(date) => handleDateChange(date, "endDate")}
              selectsEnd
              startDate={formData.startDate}
              endDate={formData.endDate}
              className="w-full border border-gray-300 rounded-md shadow-sm p-2"
              placeholderText="End Date"
              showTimeSelect
            />
          </div>
        </div>
        {/* Uncomment when ready for venue selection */}
        {/* <div>
          <label htmlFor="venue" className="block text-sm font-medium">Venue</label>
          <select
            id="venue"
            name="venueId"
            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm p-2"
            value={formData.venueId}
            onChange={handleInputChange}
          >
            <option value="">Select a venue</option>
            {venues.map((venue) => (
              <option key={venue.id} value={venue.id}>
                {venue.name}
              </option>
            ))}
          </select>
        </div> */}
        <div>
          <button
            type="submit"
            className="w-full bg-black text-white rounded-md py-2 hover:bg-gray-900"
            disabled={isLoading}
          >
            {isLoading ? "Loading..." : "Create Event"}
          </button>
        </div>
        {errorMsg && <p className="text-red-500">{errorMsg}</p>}
        {infoMsg && <p className="text-green-500">{infoMsg}</p>}
      </form>
    </div>
  );
};

export default EventCreationForm;
