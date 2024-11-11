import React, { useState } from 'react';
import axios from 'axios';

function UrlShortenerForm() {
  const [shortUrl, setShortUrl] = useState('');
  const [formData, setFormData] = useState({
    longUrl: '',
    expirationTimeInHours: 1
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if(!formData.longUrl.trim()){
      alert('you cant shorten nothing.... put a link in!');
      return;
    }
    try {
      const response = await axios.post('http://localhost:9000/api/v1/url/shorten', formData, {
        params: { expirationTimeInHours: formData.expirationTimeInHours }
      });
      setShortUrl(response.data);
    } catch (error) {
      console.error('Error shortening the URL', error);
    }
  };

  return (
    <div className ="url-shortener-div">
          <h1>ShortyURL!</h1>
      <form onSubmit={handleSubmit} className="url-shortener-form">
        <label>
          Enter your URL to shorten:
          <input type="text" name="longUrl" value={formData.longUrl} onChange={handleInputChange} required />
        </label>
    
        <label>
          Select expiration time:
          <select name="expirationTimeInHours" value={formData.expirationTimeInHours} onChange={handleInputChange}>
            <option value={1}>1 hour</option>
            <option value={3}>3 hours</option>
            <option value={6}>6 hours</option>
          </select>
        </label>

        <button type="submit">Shorten URL</button>
      </form>

      {shortUrl && (
        <div className = "url-shortener-div2">
          <p>Your shortened URL: <a href={shortUrl}>{shortUrl}</a></p>
        </div>
      )}
    </div>
  );
}

export default UrlShortenerForm;