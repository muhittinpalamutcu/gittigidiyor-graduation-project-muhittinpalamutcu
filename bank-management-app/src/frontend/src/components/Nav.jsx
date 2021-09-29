import React, { useState, useEffect } from "react";
import { Link, useLocation } from "react-router-dom";

const Nav = () => {
  const [pathname, setPathname] = useState("");
  const [showNav, setShownav] = useState(false);
  const [miniMapCustomer, setMiniMapCustomer] = useState(false);
  const [miniMapCreditApplication, setMiniMapCreditApplication] =
    useState(false);

  let location = useLocation();

  useEffect(() => {
    const { pathname } = location;
    setPathname(pathname);
    if (
      pathname === "/customer-registery" ||
      pathname === "/customer-search" ||
      pathname === "/customer-update"
    ) {
      setMiniMapCustomer(true);
      setMiniMapCreditApplication(false);
    } else if (
      pathname === "/credit-information-inquiry" ||
      pathname === "/credit-application"
    ) {
      setMiniMapCustomer(false);
      setMiniMapCreditApplication(true);
    } else {
      setMiniMapCustomer(false);
      setMiniMapCreditApplication(false);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [location.pathname]);

  const btnClick = () => {
    showNav ? setShownav(false) : setShownav(true);
  };

  const miniMapCustomerHandler = () => {
    setMiniMapCustomer(miniMapCustomer ? false : true);
  };

  const miniMapCreditApplicationHandler = () => {
    setMiniMapCreditApplication(miniMapCreditApplication ? false : true);
  };

  return (
    <nav className="text-right">
      <div>
        <div className="font-bold uppercase p-4 border-b border-gray-100 flex justify-between items-center">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            className="h-5 w-5 lg:hidden"
            viewBox="0 0 20 20"
            fill="currentColor"
            w
          >
            <path d="M4 4a2 2 0 00-2 2v1h16V6a2 2 0 00-2-2H4z" />
            <path
              fillRule="evenodd"
              d="M18 9H2v5a2 2 0 002 2h12a2 2 0 002-2V9zM4 13a1 1 0 011-1h1a1 1 0 110 2H5a1 1 0 01-1-1zm5-1a1 1 0 100 2h1a1 1 0 100-2H9z"
              clipRule="evenodd"
            />
          </svg>
          <div>
            <a href="/" className="hover:text-gray-800">
              Bank Management App
            </a>
          </div>
          <div className="px-4 cursor-pointer md:hidden">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              className="w-6"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
              onClick={btnClick}
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth={2}
                d="M4 6h16M4 12h16M4 18h16"
              />
            </svg>
          </div>
        </div>
      </div>
      <ul className={`text-sm mt-6 ${showNav ? "" : "hidden"} md:block`}>
        <li
          className="text-gray-700 font-bold py-1 cursor-pointer"
          onClick={miniMapCustomerHandler}
        >
          <div className="flex px-4 justify-end border-r-4 border-white">
            <span>Customer</span>
            <svg
              xmlns="http://www.w3.org/2000/svg"
              className="w-5 ml-2"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth={2}
                d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6"
              />
            </svg>
          </div>
          {miniMapCustomer && (
            <ul className="text-xs font-normal">
              <li
                className={`py-1 ${
                  pathname === "/customer-registery"
                    ? "border-primary border-r-4"
                    : "border-white"
                }`}
              >
                <Link
                  to="/customer-registery"
                  className="flex px-4 justify-end border-r-4 border-white"
                >
                  <span>Customer Registery</span>
                </Link>
              </li>
              <li
                className={`py-1 ${
                  pathname === "/customer-search"
                    ? "border-primary border-r-4"
                    : "border-white"
                }`}
              >
                <Link
                  to="/customer-search"
                  className="flex px-4 justify-end border-r-4 border-white"
                >
                  <span>Search Customer</span>
                </Link>
              </li>
              <li
                className={`py-1 ${
                  pathname === "/customer-update"
                    ? "border-primary border-r-4"
                    : "border-white"
                }`}
              >
                <Link
                  to="/customer-update"
                  className="flex px-4 justify-end border-r-4 border-white"
                >
                  <span>Update Customer</span>
                </Link>
              </li>
            </ul>
          )}
        </li>

        <li className="py-1 font-bold">
          <div
            className="flex px-4 justify-end border-r-4 border-white cursor-pointer"
            onClick={miniMapCreditApplicationHandler}
          >
            <span>Credit</span>
            <svg
              xmlns="http://www.w3.org/2000/svg"
              className="w-5 ml-2"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth={2}
                d="M13 10V3L4 14h7v7l9-11h-7z"
              />
            </svg>
          </div>
        </li>
        {miniMapCreditApplication && (
          <ul className="text-xs font-normal">
            <li
              className={`py-1 ${
                pathname === "/credit-information-inquiry"
                  ? "border-primary border-r-4"
                  : "border-white"
              }`}
            >
              <Link
                to="/credit-information-inquiry"
                className="flex px-4 justify-end border-r-4 border-white"
              >
                <span>Credit Information Inquiry</span>
              </Link>
            </li>
            <li
              className={`py-1 ${
                pathname === "/credit-application"
                  ? "border-primary border-r-4"
                  : "border-white"
              }`}
            >
              <Link
                to="/credit-application"
                className="flex px-4 justify-end border-r-4 border-white"
              >
                <span>Credit Application</span>
              </Link>
            </li>
          </ul>
        )}
      </ul>
    </nav>
  );
};

export default Nav;
