import React, { useState } from "react";
import axios from "axios";
import { FaCalendarAlt, FaMapMarkerAlt } from "react-icons/fa";
import { EventHighlightLoader } from "../globals/EventDetailsLoader";
import { useSelector } from "react-redux";
import { Link } from "react-router-dom";

function EventDetailPage({ event }) {
  const user = useSelector((state) => state.user.user);
  console.log(user);
  const [comments, setComments] = useState([]);
  const [newComment, setNewComment] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [loading, setLoading] = useState(false);

  const handleCommentChange = (e) => {
    setNewComment(e.target.value);
  };

  const handleCommentSubmit = async (e) => {
    e.preventDefault();
    setIsSubmitting(true);

    try {
      const response = await axios.post(
        `/api/events/${event.eventId}/comments`,
        {
          content: newComment,
        }
      );

      if (response.status === 200) {
        setComments([...comments, response.data]);
        setNewComment("");
      }
    } catch (error) {
      console.error("Error submitting comment:", error);
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className="w-full  ">
      {loading ? (
        <EventHighlightLoader />
      ) : (
        <div className="w-full flex flex-col sm:flex-row events-header  pt-16 imaage">
          <div className="w-full sm:w-3/4 text-white gap-8 flex flex-col px-12 sm:px-10 justify-center text-2xl ">
            <h1 className="text-2xl lg:text-4xl font-medium ">
              {event?.title}
            </h1>
            <span className="flex gap-4">
              <FaCalendarAlt />
              <p className="text-white">
                {new Date(event.startTime).toLocaleString()} -{" "}
                {new Date(event.endTime).toLocaleString()}
              </p>
            </span>
            <span className="flex gap-6">
              <FaMapMarkerAlt />
              <p>{event?.location}</p>
            </span>
            <span className="flex gap-6">
              <FaMapMarkerAlt />
              <p className="text-orange">
                Organized by: {event.organizer.fullName}
              </p>
            </span>
          </div>
        </div>
      )}

      <div className="flex flex-col mx-6 lg:flex-row">
        <div className="w-full  mb-6 lg:mb-0">
          <div className="bg-white shadow-md rounded mb-6 p-6">
            <h2 className="text-2xl font-bold mb-4">Event Description</h2>
            <p className="text-gray-700 mb-4">{event.description}</p>

            <h2 className="text-2xl font-bold mb-4">Schedule</h2>
            <p className="text-gray-700 mb-4">
              Details about the event schedule can be placed here.
            </p>

            <h2 className="text-2xl font-bold mb-4">Ticket Options</h2>
            <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
              <div className="border p-4 rounded">
                <h3 className="text-xl font-semibold">Basic</h3>
                <p className="text-gray-700 mb-4">
                  Price: ${event.ticketPricing.basicPrice}
                </p>
                <Link className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline" to={`/events/ticket-register/${event?.eventId}`} state={event}>
                  Purchase Basic
                </Link>
              </div>
              <div className="border p-4 rounded">
                <h3 className="text-xl font-semibold">Standard</h3>
                <p className="text-gray-700 mb-4">
                  Price: ${event.ticketPricing.standardPrice}
                </p>
                <Link className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline" to={`/events/ticket-register/${event?.eventId}`} state={event}>
                  Purchase Standard
                </Link>
              </div>
              <div className="border p-4 rounded">
                <h3 className="text-xl font-semibold">Premium</h3>
                <p className="text-gray-700 mb-4">
                  Price: ${event.ticketPricing.premiumPrice}
                </p>
                <Link className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline" to={`/events/ticket-register/${event?.eventId}`} state={event}>
                  Purchase Premium
                </Link>
              </div>
            </div>
          </div>
        </div>
      </div>

      <footer className="bg-white shadow-md rounded mt-6 p-6">
        <h2 className="text-2xl font-bold mb-4">Comments & Discussion</h2>
        {user && (
          <form onSubmit={handleCommentSubmit} className="mb-4">
            <textarea
              className="w-full p-4 border rounded mb-4"
              placeholder="Add a comment..."
              value={newComment}
              onChange={handleCommentChange}
              required
            />
            <button
              type="submit"
              className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
              disabled={isSubmitting}
            >
              {isSubmitting ? "Submitting..." : "Post Comment"}
            </button>
          </form>
        )}
        <div className="mb-6">
          <div className="space-y-4">
            {comments.map((comment, index) => (
              <div key={index} className="p-4 border rounded">
                <p className="text-gray-800">{comment.content}</p>
                <p className="text-gray-500 text-sm">
                  {new Date(comment.createdAt).toLocaleString()}
                </p>
              </div>
            ))}
          </div>
        </div>

        
        <div className="mb-6">
          <h2 className="text-2xl font-bold mb-4">FAQs</h2>
          <div className="space-y-4">
            <details className="border rounded-md p-4">
              <summary className="font-semibold">
                What is the refund policy?
              </summary>
              <p className="mt-2 text-gray-700">
                Details about the refund policy.
              </p>
            </details>
            <details className="border rounded-md p-4">
              <summary className="font-semibold">Is parking available?</summary>
              <p className="mt-2 text-gray-700">
                Details about parking availability.
              </p>
            </details>
            <details className="border rounded-md p-4">
              <summary className="font-semibold">
                Can I transfer my ticket?
              </summary>
              <p className="mt-2 text-gray-700">
                Details about ticket transfer.
              </p>
            </details>
          </div>
        </div>
      </footer>
    </div>
  );
}

export default EventDetailPage;
