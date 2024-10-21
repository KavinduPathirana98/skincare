import Appointments from "./pages/appointments"; // Fix typo here (Appoinments -> Appointments)
import Patients from "./pages/patients";
import Treatments from "./pages/treatment";
import Invoice from "./pages/invoice";
import { Link, Route, BrowserRouter as Router, Routes } from "react-router-dom"; // Use BrowserRouter instead of Router
import "react-toastify/dist/ReactToastify.css";
import { ToastContainer } from "react-toastify";
import "bootstrap/dist/css/bootstrap.min.css";
import Container from "react-bootstrap/Container";
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
function App() {
  return (
    <Router>
      <ToastContainer></ToastContainer>
      <>
        <Navbar bg="dark" data-bs-theme="dark">
          <Container>
            <Navbar.Brand href="/appointments">Aurora Skin Care </Navbar.Brand>
            <Nav className="me-auto">
              <Nav.Link href="/appointments">Appointments</Nav.Link>
              <Nav.Link href="/patients">Patients</Nav.Link>
              <Nav.Link href="/treatments">Treatments</Nav.Link>
              <Nav.Link href="/invoice">Invoices</Nav.Link>
            </Nav>
          </Container>
        </Navbar>
      </>
      <div>
        {/* <nav>
          <ul>
            <li>
              <Link to="/appointments">Appointments</Link>
            </li>
            <li>
              <Link to="/patients">Patients</Link>
            </li>
            <li>
              <Link to="/treatments">Treatments</Link>
            </li>
            <li>
              <Link to="/invoice">Invoice</Link>
            </li>
          </ul>
        </nav> */}

        {/* Define routes for the different components */}
        <Routes>
          <Route path="/appointments" element={<Appointments />} />
          <Route path="/patients" element={<Patients />} />
          <Route path="/treatments" element={<Treatments />} />
          <Route path="/invoice" element={<Invoice />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
