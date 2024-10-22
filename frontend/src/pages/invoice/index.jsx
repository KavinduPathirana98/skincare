import { Fragment, useEffect, useRef, useState } from "react";
import {
  deleteInvoice,
  getAllAppointments,
  getAllInvoices,
  getAllTreatments,
  saveInvoice,
  searchInvoiceByID,
  updateInvoice,
} from "../../service";
import { Button, Col, Form, Input, Row, Select, Space, Table, Tag } from "antd";
import "antd/dist/antd";
import axios from "axios";
import {
  AddKey,
  api_url,
  errMsgDelete,
  errMsgGet,
  errMsgUpdate,
  insertString,
  invoicesRoute,
  succMsgDelete,
  succMsgUpdate,
  UpdateKey,
} from "../../constants";
import { toast } from "react-toastify";
import {
  Container,
  Label,
  Modal,
  ModalBody,
  ModalFooter,
  ModalHeader,
} from "reactstrap";
import { render } from "@testing-library/react";
import {
  DeleteOutlined,
  EditOutlined,
  FilePdfOutlined,
  PlusOutlined,
} from "@ant-design/icons";
import { useForm } from "antd/es/form/Form";
import moment from "moment";
import Print from "./print";

const Invoice = () => {
  const [invoices, setInvoices] = useState([]);
  const [appointment, setAppoinments] = useState([]);
  const [treatments, setTreatments] = useState([]);
  const [modal, setModal] = useState(false);
  const [header, setHeader] = useState(AddKey);
  const [modalState, setModalState] = useState(AddKey);
  const [updateInvoiceID, setUpdateInvoiceID] = useState();
  const [form] = Form.useForm();
  const [inID, setInID] = useState();
  const columns = [
    {
      title: "Invoice No",
      dataIndex: "id",
      key: "id",
    },
    {
      title: "Name",
      render: (record) =>
        `${record.appointment.patient.fName} ${record.appointment.patient.lName}`,
      key: "name",
    },
    {
      title: "Appointment No",
      render: (record) => `${record.appointment.id} `,
      key: "appointmentNo",
    },
    {
      title: "Dermatologist",
      render: (record) => `${record.appointment.dermatologist} `,
      key: "dermatologist",
    },
    {
      title: "Registration Fee",
      render: (record) => `500`,
      key: "reg",
    },
    {
      title: "TreatmentCost",
      render: (record) => `${record.treatmentCost} `,
      key: "treatmentCost",
    },
    {
      title: "Tax",
      render: (record) => `2.5%`,
      key: "tax",
    },
    {
      title: "Total",
      render: (record) => `${record.totalAmount} `,
      key: "total",
    },

    {
      align: "center",
      title: () => {
        return (
          <tr>
            <td>
              <Button
                style={{ marginLeft: "15px" }}
                onClick={() => toggle(AddKey)}
              >
                <PlusOutlined />
              </Button>
            </td>
          </tr>
        );
      },

      render: (_, record) => {
        return (
          <tr>
            <td>
              <Button onClick={() => setInID(record)}>
                <FilePdfOutlined />
              </Button>
            </td>
            <td>
              <Button>
                <DeleteOutlined
                  onClick={() => {
                    deletefromID(record.id);
                  }}
                />
              </Button>
            </td>
          </tr>
        );
      },
      dataIndex: "actions",
    },
  ];
  const invoiceRef = useRef();

  //Calling getall from service
  const getall = () => {
    getAllInvoices()
      .then((response) => setInvoices(response.data.data))
      .catch((err) => toast.error(errMsgGet));
  };
  const getallAppointments = () => {
    getAllAppointments()
      .then((response) => setAppoinments(response.data.data))
      .catch((err) => toast.error(errMsgGet));
  };
  const getallTreatments = () => {
    getAllTreatments()
      .then((response) => setTreatments(response.data.data))
      .catch((err) => toast.error(errMsgGet));
  };

  //Calling update from service
  const update = async () => {
    try {
      const formValues = await form.validateFields();
      formValues.id = updateInvoiceID;
      updateInvoice(formValues)
        .then((response) => {
          if (response.data.responseCode == 1) {
            getall();
            toast.success(succMsgUpdate);
          } else {
          }
        })
        .catch((err) => toast.error(errMsgUpdate));
    } catch (err) {}
  };
  //Calling delete from service
  const deletefromID = (id) => {
    deleteInvoice(id)
      .then((response) => {
        console.log(response);
        if (response.data.responseCode == 1) {
          toast.success(succMsgUpdate);
          getall();
        } else {
          toast.error(errMsgDelete);
        }
      })
      .catch((err) => toast.error(errMsgGet));
  };
  //Calling search from service
  const searchfromID = () => {
    setInvoices([]); // Clear previous invoices

    searchInvoiceByID(keyword)
      .then((response) => {
        const invoicesData = response.data.data;

        // Check if invoicesData is an array and set it, or set it to an empty array if not
        if (Array.isArray(invoicesData)) {
          setInvoices(invoicesData);
        } else {
          let x = [invoicesData];
          setInvoices(x); // Handle unexpected response format
        }
      })
      .catch((err) => {
        console.error(err); // Log the error to debug
        toast.error(errMsgGet); // Display an error notification
      });
  };
  //Calling insert from service
  const insert = async () => {
    try {
      const formValues = await form.validateFields();
      const filteredTreatment = treatments.filter((item) => {
        return item.id === formValues.treatment;
      });
      const filteredAppointment = appointment.filter((item) => {
        return item.id === formValues.appointment;
      });
      formValues.appointment = filteredAppointment[0];
      formValues.treatment = filteredTreatment[0];
      formValues.treatmentCost = Number(
        parseFloat(formValues.treatmentCost).toFixed(2)
      );
      console.log(formValues);
      saveInvoice(formValues)
        .then((response) => {
          if (response.data.responseCode == 1) {
            getall();
            toast.success(succMsgUpdate);
          } else {
          }
        })
        .catch((err) => toast.error(errMsgUpdate));
    } catch (err) {}
  };
  //onchange treatment
  const onChangeTreatment = (id) => {
    const price = treatments.filter((item) => {
      return item.id === id;
    });
    form.setFieldValue("treatmentCost", parseFloat(price[0].price).toFixed(2));

    form.setFieldValue(
      "totalAmount",
      (parseFloat(price[0].price).toFixed(2) *
        (Number(parseFloat(form.getFieldValue("tax").toFixed(2))) +
          Number(parseFloat("100").toFixed(2)))) /
        100 +
        500
    );
  };
  const toggle = (key, record) => {
    if (record) {
      setUpdateInvoiceID(record.id);
      form.setFieldValue("InvoiceName", record.InvoiceName);
      form.setFieldValue("price", record.price);
    }

    setModalState(key);
    if (key === UpdateKey) {
      setHeader(UpdateKey + " Invoice  ( ID:" + record.id + ")");
    } else {
      setHeader(insertString + " Invoice");
    }
    setModal(!modal);
  };

  useEffect(() => {
    getall();
    getallAppointments();
    getallTreatments();
  }, []);
  const Submit = () => {
    if (modalState == AddKey) {
      insert();
    } else {
      update();
    }
  };
  const [keyword, setKeyword] = useState("");
  return (
    <Fragment>
      <br></br>
      <Row>
        <Col md={3}></Col>
        <Col md={18}>
          <Row>
            <Col md={20}>
              <Input
                value={keyword}
                onChange={(e) => {
                  setKeyword(e.target.value);
                }}
                placeholder="search Invoice by ID"
              />
            </Col>
            <Col md={4}>
              <Button
                onClick={() => {
                  searchfromID();
                }}
              >
                Search
              </Button>
            </Col>
          </Row>
        </Col>
        <Col md={3}></Col>
      </Row>

      <br></br>
      <Row>
        <Col md={3}></Col>
        <Col md={18}>
          <Table columns={columns} dataSource={invoices && invoices} />
        </Col>
        <Col md={3}></Col>
      </Row>
      <Modal isOpen={modal} toggle={() => toggle(AddKey)} size="md">
        <ModalHeader toggle={() => toggle(AddKey)}>{header}</ModalHeader>
        <ModalBody>
          <Form
            form={form}
            initialValues={{ tax: 2.5, appointment: -1, treatment: -1 }}
          >
            <Row>
              <Col md={24}>
                <Label>Appointment</Label>
                <Form.Item
                  name={"appointment"}
                  rules={[
                    { required: true, message: "Required" },
                    {
                      validator: (_, value) => {
                        if (value === -1) {
                          return Promise.reject();
                        } else {
                          return Promise.resolve();
                        }
                      },
                      message: "Required",
                    },
                  ]}
                >
                  <Select>
                    <Select.Option value={-1}>
                      ---Select Appointment---
                    </Select.Option>
                    {appointment &&
                      appointment.map((item) => {
                        return (
                          <Select.Option value={item.id}>
                            {item.patient.fName + " " + item.patient.lName} -{" "}
                            {moment(item.appointmentDate).format(
                              "yyyy-MM-DD  (HH:mm:ss)"
                            )}
                          </Select.Option>
                        );
                      })}
                  </Select>
                </Form.Item>
              </Col>
            </Row>
            <Row>
              <Col md={11}>
                <Label>Treatment</Label>
                <Form.Item
                  name={"treatment"}
                  rules={[
                    { required: true, message: "Required" },
                    {
                      validator: (_, value) => {
                        if (value === -1) {
                          return Promise.reject();
                        } else {
                          return Promise.resolve();
                        }
                      },
                      message: "Required",
                    },
                  ]}
                >
                  <Select
                    onChange={(id) => {
                      onChangeTreatment(id);
                    }}
                  >
                    <Select.Option value={-1}>
                      ---Select Treatment---
                    </Select.Option>
                    {treatments &&
                      treatments.map((item) => {
                        return (
                          <Select.Option value={item.id}>
                            {item.treatmentName}
                          </Select.Option>
                        );
                      })}
                  </Select>
                </Form.Item>
              </Col>
              <Col md={2}></Col>
              <Col md={11}>
                <Label>Treatment Cost</Label>
                <Form.Item
                  name={"treatmentCost"}
                  rules={[{ required: true, message: "Required" }]}
                >
                  <Input type="number" disabled={true} />
                </Form.Item>
              </Col>
            </Row>
            <Row>
              <Col md={11}>
                <Label>Tax</Label>
                <Form.Item
                  name={"tax"}
                  rules={[{ required: true, message: "Required" }]}
                >
                  <Input type="number" disabled={true} />
                </Form.Item>
              </Col>
              <Col md={2}></Col>
              <Col md={11}>
                <Label>Total Amount</Label>
                <Form.Item
                  name={"totalAmount"}
                  rules={[{ required: true, message: "Required" }]}
                >
                  <Input type="number" disabled={true} />
                </Form.Item>
              </Col>
            </Row>
          </Form>
        </ModalBody>
        <ModalFooter>
          <Button
            onClick={() => {
              Submit();
            }}
            color="default"
            variant="solid"
          >
            {"Submit"}
          </Button>
        </ModalFooter>
      </Modal>
      {inID ? <Print data={inID} /> : ""}
    </Fragment>
  );
};
export default Invoice;
