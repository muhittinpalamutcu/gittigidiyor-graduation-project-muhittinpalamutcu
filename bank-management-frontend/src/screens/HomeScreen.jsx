import React from "react";

const HomeScreen = () => {
  return (
    <div className="grid grid-cols-4 mt-20 h-full">
      <div></div>
      <div className="text-right col-span-3">
        <h1 className="text-5xl text-gray-600 font-bold">Welcome to</h1>
        <h1 className="text-8xl text-gray-900 font-bold">Bank</h1>
        <h1 className="text-8xl text-gray-900 font-bold">Management</h1>
        <h1 className="text-7xl text-primary font-bold">App</h1>
      </div>
    </div>
  );
};

export default HomeScreen;
