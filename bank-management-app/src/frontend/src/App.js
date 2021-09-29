import React, { useState, useEffect } from "react";
import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import { useSelector } from "react-redux";
import Nav from "./components/Nav";
import CustomerRegisteryScreen from "./screens/CustomerRegisteryScreen";
import CustomerSearchScreen from "./screens/CustomerSearchScreen";
import CustomerUpdateScreen from "./screens/CustomerUpdateScreen";
import CreditInformationInquiryScreen from "./screens/CreditInformationInquiryScreen";

const App = () => {
  const [newRegistery, setNewRegistery] = useState(false);
  const customerRegister = useSelector((state) => state.customerRegister);
  const { customerInfo } = customerRegister;

  useEffect(() => {
    if (customerInfo) {
      if (!customerInfo.identityNumber) {
        setNewRegistery(true);
      }
    }
  }, [customerInfo]);

  return (
    <Router>
      <div className="text-gray-600 font-body grid md:grid-cols-12 lg:grid-cols-8">
        <div className="md:col-span-2 lg:col-span-1 md:flex md:justify-end">
          <Nav />
        </div>
        <div className="px-16 py-6 bg-gray-100 md:col-span-10 lg:col-span-7 h-screen">
          <div className="flex justify-center md:justify-end">
            {customerInfo && !newRegistery ? (
              <div>
                <p className="font-light uppercase text-sm underline">
                  <span className="font-bold text-primary">Identity: </span>
                  {customerInfo.identityNumber} {customerInfo.fullName}
                </p>
              </div>
            ) : (
              <Link
                to="/customer-registery"
                className="btn text-primary border-primary md:border-2 hover:bg-primary hover:text-white transition ease-out duration-500"
              >
                Registery
              </Link>
            )}
          </div>
          <main>
            <Route
              path="/customer-registery"
              component={CustomerRegisteryScreen}
            />
            <Route path="/customer-search" component={CustomerSearchScreen} />
            <Route path="/customer-update" component={CustomerUpdateScreen} />
            <Route
              path="/credit-information-inquiry"
              component={CreditInformationInquiryScreen}
            />
          </main>
          <div className="text-center mt-10 font-light text-xs">
            © Copyright 2021 @mui.coding-"Muhittin Palamutçu"
          </div>
        </div>
      </div>
    </Router>
  );
};

export default App;
