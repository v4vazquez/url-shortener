import UrlShortenerForm from './components/form';
import Footer from './components/footer';
import './App.css'
import Tooltip from './components/tooltip';
import '@fortawesome/fontawesome-free/css/all.min.css';


function App() {
  return (
    <div className="App">
       <Tooltip message="This was built as part of John Cricket's coding challenges, it is a TinyURL clone. The stack is Spring, PostgreSQL, React and Cucumber. 
       simply put a link, have it shortened, and it expires. Github repo can be accessed in the bottom right" />
      <UrlShortenerForm />
      <Footer/>
    </div>
  );
}

export default App
