import React, { useRef } from "react";
import jsPDF from "jspdf";
import html2canvas from "html2canvas";

const Print = ({ data }) => {
  const invoiceRef = useRef();

  // Sample invoice data passed as prop or hardcoded for now
  //   const data = {
  //     id: data.id,
  //     appointment: {
  //       id: data.appointment.id,
  //       patient: {
  //         fName: data.appointment.patient.fName,
  //         lName: data.appointment.patient.lName,
  //         email: data.appointment.patient.email,
  //         phone: data.appointment.patient.phone,
  //         nic: data.appointment.patient.nic,
  //       },
  //       dermatologist: data.appointment.dermatologist,
  //       appointmentDate: new Date(data.appointment.appointmentDate).toLocaleDateString(),
  //       registrationFee: data.appointment.registrationFee,
  //       paid: data.appointment.paid ? 'Yes' : 'No',
  //     },
  //     treatmentCost: data.treatmentCost,
  //     tax: data.tax,
  //     totalAmount: data.totalAmount,
  //   };

  const generatePDF = () => {
    const input = invoiceRef.current;
    html2canvas(input, { scale: 2 }).then((canvas) => {
      const imgData = canvas.toDataURL("image/png");
      const pdf = new jsPDF("p", "mm", "a4");
      const imgWidth = 210; // A4 width in mm
      const pageHeight = 295; // A4 height in mm
      const imgHeight = (canvas.height * imgWidth) / canvas.width;
      let heightLeft = imgHeight;
      let position = 0;

      pdf.addImage(imgData, "PNG", 0, position, imgWidth, imgHeight);
      heightLeft -= pageHeight;

      while (heightLeft >= 0) {
        position = heightLeft - imgHeight;
        pdf.addPage();
        pdf.addImage(imgData, "PNG", 0, position, imgWidth, imgHeight);
        heightLeft -= pageHeight;
      }

      pdf.save(`invoice_${data.id}.pdf`);
    });
  };

  return (
    <div>
      <div className="row">
        <div className="col-md-4"></div>
        <div className="col-md-4">
          &nbsp; &nbsp;
          <button
            className="btn btn-primary"
            onClick={generatePDF}
            style={styles.button}
          >
            Download PDF
          </button>
        </div>
        <div className="col-md-4"></div>
      </div>
      <br></br>
      <div ref={invoiceRef} style={styles.invoiceContainer}>
        <header style={styles.header}>
          <h1>INVOICE</h1>
          <div>
            <p>Invoice ID: {data.id}</p>
            <p>Appointment ID: {data.appointment.id}</p>
            <p>Date: {new Date().toLocaleDateString()}</p>
          </div>
        </header>

        <section style={styles.billToSection}>
          <h3>Patient Information:</h3>
          <p>
            Name:{" "}
            {`${data.appointment.patient.fName} ${data.appointment.patient.lName}`}
          </p>
          <p>Email: {data.appointment.patient.email}</p>
          <p>Phone: {data.appointment.patient.phone}</p>
          <p>NIC: {data.appointment.patient.nic}</p>
        </section>

        <section style={styles.detailsSection}>
          <h3>Appointment Details:</h3>
          <p>Dermatologist: {data.appointment.dermatologist}</p>
          <p>Appointment Date: {data.appointment.appointmentDate}</p>
          <p>Registration Fee: {data.appointment.registrationFee.toFixed(2)}</p>
          <p>Paid: {data.appointment.paid}</p>
        </section>

        <section style={styles.detailsSection}>
          <h3>Charges:</h3>
          <p>Treatment Cost: {data.treatmentCost.toFixed(2)}</p>
          <p>Tax: {data.tax}%</p>
          <h2>Total Amount: {data.totalAmount.toFixed(2)}</h2>
        </section>
      </div>
    </div>
  );
};

const styles = {
  invoiceContainer: {
    width: "600px",
    margin: "0 auto",
    padding: "20px",
    border: "1px solid #ddd",
    borderRadius: "8px",
    fontFamily: "Arial, sans-serif",
    fontSize: "12px",
  },
  header: {
    display: "flex",
    justifyContent: "space-between",
    borderBottom: "1px solid #ddd",
    paddingBottom: "10px",
    marginBottom: "20px",
  },
  billToSection: {
    marginBottom: "20px",
  },
  detailsSection: {
    marginBottom: "20px",
  },
  button: {
    padding: "10px 20px",
    fontSize: "14px",
    cursor: "pointer",
  },
};

export default Print;
