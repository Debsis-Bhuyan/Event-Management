import { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import {
  FaChevronRight,
  FaUsers,
  FaGlobe,
  FaPlus,
  FaCalendarAlt,
  FaCalendarCheck,
  FaLocationArrow,
} from "react-icons/fa";
import { useDispatch, useSelector } from "react-redux";
import axios from "axios";
import { APP_URL } from "../../utils";

import{ setUserEvent} from "../../store/userEventSlice" 
const CardLoader = () => {
  return (
    <div className="w-[250px] h-[100px] flex justify-start gap-2 items-center bg-white px-4 border border-gray-100 rounded-md">
      <div className="bg-gray-600 rounded-[20px] h-[120px] w-[80px] animate-pulse"></div>
      <div className="flex flex-col justify-center">
        <div className="bg-gray-600 h-[50px] w-[80px] mb-2 animate-pulse"></div>
        <div className="bg-gray-600 h-[30px] w-[150px] animate-pulse"></div>
      </div>
    </div>
  );
};

const Summary = () => {
  const dispatch = useDispatch();
  const eventmy = useSelector(state => state.userEvent.userEvent)
  const navigate = useNavigate();
  const user = useSelector((state) => state.user.user)?.user;
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [userEvents, setUserEvents] = useState(eventmy);

  useEffect(() => {
    const getUserEvents = async () => {
      setLoading(true);
  
      try {
        const response = await axios.get(`${APP_URL}/event/get-events/1`);
        setUserEvents(response.data?.events)
        dispatch(setUserEvent(response.data?.events))
      } catch (error) {
        console.error("Error fetching user events:", error);
        setUserEvents([])
      } finally {
        setLoading(false);
      }
    };
  
    getUserEvents(); 
  }, []);

  useEffect(() => {
    if (userEvents) {
      // const registered_events = userEvents?.filter((event) => {
      //   return event.attendees.find((_user) => _user.id === user.id);
      // });
      const user_past_events = userEvents.filter((event) => {
        return new Date(event.endTime) <= new Date();
      });
      
      const user_upcoming_events = userEvents.filter((event) => {
        return new Date(event.startTime) > new Date();
      });
      // const registered_past_events = registered_events.filter((event) => {
      //   return new Date(event.start_date) <= new Date();
      // });
      // const registered_upcoming_events = registered_events.filter((event) => {
      //   return new Date(event.start_date) > new Date();
      // });
      // const registered_users = user_events.reduce(
      //   (accumulator, event) => accumulator + event.attendees.length,
      //   0
      // );
      // const registered_users = userEvents.reduce(
      //   (accumulator, event) => accumulator + event?.registrations.length,
      //   0
      // );
      setData({
        user_past_events,
        user_upcoming_events,
        userEvents,
        // registered_users,
      });
    }
  }, [userEvents]);

 


  
  return (
    <div className="flex flex-col gap-4">
      <div>
        <h2 className="py-2 text-lg">Events you are managing</h2>
        <div className=" w-full  md:grid-cols-2 grid gap-4">
          {data ? (
            <div className="w-full md:w-[360px] h-[100px] p-4 bg-white shadow-sm rounded-md">
             
                <div className="flex items-center gap-4">
                  <div className="bg-blue-500 text-white p-4 rounded-full">
                    <FaCalendarAlt size={30} />
                  </div>
                  <div>
                    <h2 className="font-bold text-2xl">
                      {data?.user_past_events?.length || 0}
                    </h2>
                    <p className="capitalize text-gray-600 text-sm">
                      Past Events
                    </p>
                  </div>
                </div>
                
              
            </div>
          ) : (
            <CardLoader />
          )}

          {data ? (
            <div className="w-full md:w-[360px] h-[100px] p-4 bg-white shadow-sm rounded-md">
              
                <div className="flex items-center gap-4">
                  <div className="bg-yellow-500 text-white p-4 rounded-full">
                    <FaCalendarCheck size={30} />
                  </div>
                  <div>
                    <h2 className="font-bold text-2xl">
                      {data?.user_upcoming_events?.length || 0}
                    </h2>
                    <p className="capitalize text-gray-600 text-sm">
                      Upcoming Events
                    </p>
                  </div>
                </div>
                
            </div>
          ) : (
            <CardLoader />
          )}

          {data ? (
            <div className="w-full md:w-[360px] h-[100px] p-4 bg-white shadow-sm rounded-md">
              <Link
                to="/dashboard/my-events"
                className="flex items-center justify-between w-full"
              >
                <div className="flex items-center gap-4">
                  <div className="bg-pink-500 text-white p-4 rounded-full">
                    <FaCalendarAlt size={30} />
                  </div>
                  <div>
                    <h2 className="font-bold text-2xl">
                      {data?.userEvents.length || 0}
                    </h2>
                    <p className="capitalize text-gray-600 text-sm">
                      All Events
                    </p>
                  </div>
                </div>
                <FaChevronRight size={20} className="text-gray-400" />
              </Link>
            </div>
          ) : (
            <CardLoader />
          )}

          {data ? (
            <div className="w-full md:w-[360px] h-[100px] p-4 bg-white shadow-sm rounded-md">
              <Link
                to="/dashboard/registered-attendee"
                className="flex items-center justify-between w-full"
              >
                <div className="flex items-center gap-4">
                  <div className="bg-green-500 text-white p-4 rounded-full">
                    <FaUsers size={30} />
                  </div>
                  <div>
                    <h2 className="font-bold text-2xl">
                      {data?.registered_users || 0}
                    </h2>
                    <p className="capitalize text-gray-600 text-sm">
                      Registered Users
                    </p>
                  </div>
                </div>
                <FaChevronRight size={20} className="text-gray-400" />
              </Link>
            </div>
          ) : (
            <CardLoader />
          )}
        </div>
      </div>

      {/* <div className="flex justify-between align-middle mt-4 py-2 gap-5 flex-wrap lg:flex-nowrap">
        <div className="w-full lg:w-1/2 py-2 bg-white shadow-sm rounded-md">
          <h2 className="pb-5 text-lg">Events you Registered for</h2>
          <div className="flex justify-between w-full flex-wrap gap-4">
            {data ? (
              <div className="w-full md:w-[250px] h-[100px] p-4 bg-white shadow-sm rounded-md">
                <div className="flex items-center gap-4">
                  <div className="bg-blue-500 text-white p-4 rounded-full">
                    <FaCalendarAlt size={30} />
                  </div>
                  <div>
                    <h2 className="font-bold text-2xl">
                      {data?.registered_past_events?.length || 0}
                    </h2>
                    <p className="capitalize text-gray-600 text-sm">
                      Past Events
                    </p>
                  </div>
                </div>
              </div>
            ) : (
              <CardLoader />
            )}

            {data ? (
              <div className="w-full md:w-[250px] h-[100px] p-4 bg-white shadow-sm rounded-md">
                <div className="flex items-center gap-4">
                  <div className="bg-yellow-500 text-white p-4 rounded-full">
                    <FaCalendarCheck size={30} />
                  </div>
                  <div>
                    <h2 className="font-bold text-2xl">
                      {data?.registered_upcoming_events?.length || 0}
                    </h2>
                    <p className="capitalize text-gray-600 text-sm">
                      Upcoming Events
                    </p>
                  </div>
                </div>
              </div>
            ) : (
              <CardLoader />
            )}
          </div>
        </div>

        <div className="w-full lg:w-1/2 py-2 bg-white shadow-sm rounded-md">
          <h2 className="pb-5 text-lg">Recommended Events</h2>
          <div className="w-full flex justify-between flex-wrap gap-4">
            <div className="w-full md:w-[250px] h-[100px] p-4 bg-white shadow-sm rounded-md">
              <div className="flex items-center gap-4">
                <div className="bg-gray-500 text-white p-4 rounded-full">
                  <FaLocationArrow size={30} />
                </div>
                <div>
                  <h2 className="font-bold text-2xl">2</h2>
                  <p className="capitalize text-gray-600 text-sm">Local</p>
                </div>
              </div>
            </div>

            <div className="w-full md:w-[250px] h-[100px] p-4 bg-white shadow-sm rounded-md">
              <div className="flex items-center gap-4">
                <div className="bg-blue-500 text-white p-4 rounded-full">
                  <FaGlobe size={30} />
                </div>
                <div>
                  <h2 className="font-bold text-2xl">8</h2>
                  <p className="capitalize text-gray-600 text-sm">
                    International
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div> */}

      <div className="mt-5 bg-white shadow-sm rounded-md p-4">
        <h2 className="py-2 text-lg">Quick Actions</h2>
        <div className="flex gap-4 pt-5 flex-wrap">
          <button
            className="px-10 py-8 border-2 border-dashed border-black rounded flex flex-col items-center gap-2 w-full lg:w-auto"
            onClick={() => {
              navigate("/dashboard/new-event");
            }}
          >
            <FaPlus className="border p-2" size={40} />
            Create Event
          </button>
        </div>
      </div>
    </div>
  );
};

export default Summary;
