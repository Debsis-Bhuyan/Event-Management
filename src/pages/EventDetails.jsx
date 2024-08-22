import React from 'react';
import Event from '../components/EventDetails';
import {useLocation, useParams} from "react-router-dom";
import Layout from './Layout';
import EventDetailPage from './EventDetailsPage';


// ok

const EventDetails = () => {
    const location =useLocation();
    const data= location.state;
    console.log(data?.event)
  const eventId = useParams().id
    return (
        <Layout>
            <EventDetailPage event={data?.event} />
            {/* <Event eventId={eventId} eventData={data}/> */}
        </Layout>
    );
};

export default EventDetails;
