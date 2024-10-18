import axios from "axios"
import { api_url, appointmentsRoute, invoicesRoute, patientsRoute, treatmentsRoute } from "../constants"
export const getAllPatients = async () => {
    try {
      const response = await axios.get(api_url + patientsRoute);
      return response; 
    } catch (err) {
      return err; 
    }
  };
export const savePatient=async(patient)=>{
    try {
        const response = await axios.post(api_url + patientsRoute,patient)
        return response; 
      } catch (err) {
        return err; 
      }
}
export const  updatePatient=async(patient)=>{
    try {
        const response = await axios.put(api_url + patientsRoute+`/${patient.id}`,patient)
        return response; 
      } catch (err) {
        return err; 
      }
}
export const deletePatient=async(id)=>{
    try {
        const response = await axios.delete(api_url + patientsRoute+`/${id}`);
        return response; 
      } catch (err) {
        return err; 
      }

}
export const searchPatientByID=async(id)=>{
    try {
        const response = await axios.get(api_url + patientsRoute+`/${id}`);
        return response; 
      } catch (err) {
        return err; 
      }
}

export const getAllAppointments = async () => {
    try {
      const response = await axios.get(api_url + patientsRoute);
      return response; 
    } catch (err) {
      return err; 
    }
  };
export const saveAppointment=async(appointment)=>{
    try {
        const response = await axios.post(api_url + appointmentsRoute,appointment)
        return response; 
      } catch (err) {
        return err; 
      }
}
export const  updateAppointment=async(appointment)=>{
    try {
        const response = await axios.put(api_url + appointmentsRoute+`/${appointment.id}`,appointment)
        return response; 
      } catch (err) {
        return err; 
      }
}
export const deleteAppointment=async(id)=>{
    try {
        const response = await axios.delete(api_url + appointmentsRoute+`/${id}`);
        return response; 
      } catch (err) {
        return err; 
      }

}
export const searchAppointmentByID=async(id)=>{
    try {
        const response = await axios.get(api_url + appointmentsRoute+`/${id}`);
        return response; 
      } catch (err) {
        return err; 
      }
}
export const getAllInvoices = async () => {
    try {
      const response = await axios.get(api_url + invoicesRoute);
      return response; 
    } catch (err) {
      return err; 
    }
  };
export const saveInvoice=async(invoice)=>{
    try {
        const response = await axios.post(api_url + invoicesRoute,invoice)
        return response; 
      } catch (err) {
        return err; 
      }
}
export const  updateInvoice=async(invoice)=>{
    try {
        const response = await axios.put(api_url + invoicesRoute+`/${invoice.id}`,invoice)
        return response; 
      } catch (err) {
        return err; 
      }
}
export const deleteInvoice=async(id)=>{
    try {
        const response = await axios.delete(api_url + invoicesRoute+`/${id}`);
        return response; 
      } catch (err) {
        return err; 
      }

}
export const searchInvoiceByID=async(id)=>{
    try {
        const response = await axios.get(api_url + invoicesRoute+`/${id}`);
        return response; 
      } catch (err) {
        return err; 
      }
}

export const getAllTreatments = async () => {
    try {
      const response = await axios.get(api_url + treatmentsRoute);
      return response; 
    } catch (err) {
      return err; 
    }
  };
export const saveTreatment=async(treatment)=>{
    try {
        const response = await axios.post(api_url + treatmentsRoute,treatment)
        return response; 
      } catch (err) {
        return err; 
      }
}
export const  updateTreatment=async(treatment)=>{
    try {
        const response = await axios.put(api_url + treatmentsRoute+`/${treatment.id}`,treatment)
        return response; 
      } catch (err) {
        return err; 
      }
}
export const deleteTreatment=async(id)=>{
    try {
        const response = await axios.delete(api_url + treatmentsRoute+`/${id}`);
        return response; 
      } catch (err) {
        return err; 
      }

}
export const searchTreatmentByID=async(id)=>{
    try {
        const response = await axios.get(api_url + treatmentsRoute+`/${id}`);
        return response; 
      } catch (err) {
        return err; 
      }
}
