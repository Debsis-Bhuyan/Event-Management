import React from "react";
import { useSelector } from "react-redux";
import {
  Navigate,
  Outlet,
  BrowserRouter,
  Route,
  Routes,
} from "react-router-dom";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Events from "./pages/Events";
import EventDetails from "./pages/EventDetails";
import OverviewPage from "./pages/OverviewPage";
import NotFound from "./pages/NotFound";
import Landing from "./pages/Landing";
import NewEvent from "./pages/NewEvent";
import UserEvents from "./pages/UserEvents.jsx";
import EditEvent from "./pages/EditEvent.jsx";
import TicketingRegistrationPage from "./pages/TicketingAndRegistration.jsx";
// import Landing from "../pages/Landing.jsx";
// import Login from "../pages/Login.jsx";
// import Events from "../pages/Events.jsx";
// import EventDetails from "../pages/EventDetails.jsx";

// import OverviewPage from "../pages/OverviewPage.jsx";

// import { getUser } from "../helpers/utils.js";
// import NotFound from "../pages/No";
// import Register from "../pages/Register.jsx";
// import Landing from "./pages/Landing.jsx";

const PrivateRoutes = () => {
  const user = useSelector((state) => state.user)?.user?.user;
  return user ? <Outlet /> : <Navigate to="/login" />;
};

const PublicRoutes = () => {
  const user = useSelector((state) => state.user).user;
  return user ? <Navigate to="/" /> : <Outlet />;
};

const App = () => {
  return (
    <div>
      <BrowserRouter>
        <div>
          <Routes>
            <Route path="*" element={<NotFound />} />
            <Route path="/" element={<Landing />} />
            <Route element={<PublicRoutes />}>
              <Route path="/login" element={<Login />} />
              <Route path="/register" element={<Register />} />
            </Route>
            <Route path="/events" element={<Events />} />
            <Route
              path="/events/ticket-register/:id"
              element={<TicketingRegistrationPage />}
            />
            <Route path="/events/:id" element={<EventDetails />} />
            <Route element={<PrivateRoutes />}>
              <Route path="/dashboard" element={<OverviewPage />} />
              <Route path="/dashboard/new-event" element={<NewEvent />} />
              <Route path="/dashboard/my-events" element={<UserEvents />} />
              <Route path="/dashboard/my-events/:id" element={<EditEvent />} />
            </Route>
          </Routes>
        </div>
      </BrowserRouter>
    </div>
  );
};

export default App;
