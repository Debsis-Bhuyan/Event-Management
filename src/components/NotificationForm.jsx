import React, { useState } from 'react';
import axios from 'axios';

function NotificationForm({ eventId }) {
  const [subject, setSubject] = useState('');
  const [message, setMessage] = useState('');
  const [isSending, setIsSending] = useState(false);
  const [status, setStatus] = useState(null);

  const handleSubjectChange = (e) => {
    setSubject(e.target.value);
  };

  const handleMessageChange = (e) => {
    setMessage(e.target.value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsSending(true);
    setStatus(null);

    try {
      const response = await axios.post(`/api/events/${eventId}/notifications`, {
        subject,
        message,
      });

      if (response.status === 200) {
        setStatus('Notification sent successfully!');
      } else {
        setStatus('Failed to send notification.');
      }
    } catch (error) {
      setStatus('Error sending notification.');
    } finally {
      setIsSending(false);
    }
  };

  return (
    <div className="container mx-auto p-6">
      <form onSubmit={handleSubmit} className="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4">
        <h2 className="text-2xl font-bold mb-4">Send Notification</h2>

        <div className="mb-4">
          <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="subject">
            Subject
          </label>
          <input
            id="subject"
            type="text"
            name="subject"
            value={subject}
            onChange={handleSubjectChange}
            className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            placeholder="Enter the subject"
            required
          />
        </div>

        <div className="mb-4">
          <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="message">
            Message
          </label>
          <textarea
            id="message"
            name="message"
            value={message}
            onChange={handleMessageChange}
            className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            placeholder="Enter your message"
            required
          />
        </div>

        <div className="flex items-center justify-between">
          <button
            type="submit"
            className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
            disabled={isSending}
          >
            {isSending ? 'Sending...' : 'Send Notification'}
          </button>
        </div>

        {status && (
          <div className={`mt-4 text-sm ${status.includes('successfully') ? 'text-green-500' : 'text-red-500'}`}>
            {status}
          </div>
        )}
      </form>
    </div>
  );
}

export default NotificationForm;
