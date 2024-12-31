#!/bin/bash

# Prompt the user for the executable name
read -p "Enter the name of the executable: " EXECUTABLE_NAME

# Validate the input
if [ -z "$EXECUTABLE_NAME" ]; then
  echo "Error: No executable name provided."
  exit 1
fi

# Run cmake in the parent directory
echo "Running cmake in the parent directory..."
cmake ..
if [ $? -ne 0 ]; then
  echo "Error: cmake failed."
  exit 1
fi

# Run make in the current directory
echo "Running make in the current directory..."
make
if [ $? -ne 0 ]; then
  echo "Error: make failed."
  exit 1
fi

# Run the executable
EXECUTABLE="./$EXECUTABLE_NAME"
if [ ! -x "$EXECUTABLE" ]; then
  echo "Error: Executable $EXECUTABLE not found or not executable."
  exit 1
fi

echo "Running $EXECUTABLE..."
$EXECUTABLE
