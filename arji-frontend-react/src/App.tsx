import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import './App.css'
import AddProject from './pages/AddProject';

const router = createBrowserRouter([
  {
    path: '/projects/add-project',
    element: <AddProject />
  }
]);

function App() {
  return <RouterProvider router={router} />
}

export default App;
