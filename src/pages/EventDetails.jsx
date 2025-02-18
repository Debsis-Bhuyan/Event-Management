import React from 'react';
import Event from '../components/EventDetails';
import {useLocation, useParams} from "react-router-dom";
import Layout from './Layout';
import EventDetailPage from './EventDetailsPage';


const EventDetails = () => {
    const location =useLocation();
    const data= location.state;
  
    return (
        <Layout>
            <EventDetailPage event={data?.event} />
        </Layout>
    );
};

export default EventDetails;
