#!/bin/bash

# Ensure fzf is installed
if ! command -v fzf &> /dev/null; then
  echo "❌ Error: fzf is not installed. Please install it first (e.g., with 'sudo apt install fzf')."
  exit 1
fi

# Print a banner
echo "============================="
echo "       Build and Run         "
echo "============================="

# Paths
PARENT_DIR="../"
SRC_DIR="$PARENT_DIR/src"
CMAKE_FILE="$PARENT_DIR/CMakeLists.txt"

# Check if src directory exists
if [ ! -d "$SRC_DIR" ]; then
  echo "❌ Error: Source directory $SRC_DIR does not exist."
  exit 1
fi

# Check if CMakeLists.txt exists
if [ ! -f "$CMAKE_FILE" ]; then
  echo "❌ Error: CMakeLists.txt not found in $PARENT_DIR."
  exit 1
fi

# Backup the original CMakeLists.txt
cp "$CMAKE_FILE" "${CMAKE_FILE}.bak"
echo "🔄 Backed up CMakeLists.txt to ${CMAKE_FILE}.bak."

# Remove all add_executable statements
echo "🧹 Removing all add_executable statements from CMakeLists.txt..."
sed -i '/^add_executable(/d' "$CMAKE_FILE"

# Add only necessary executables
echo "🔍 Scanning for .cpp files with a main function in $SRC_DIR..."
CPP_FILES=$(find "$SRC_DIR" -maxdepth 1 -type f -name "*.cpp")
if [ -z "$CPP_FILES" ]; then
  echo "❌ No .cpp files found in $SRC_DIR."
  mv "${CMAKE_FILE}.bak" "$CMAKE_FILE"
  exit 1
fi

NECESSARY_EXECUTABLES=()
for FILE in $CPP_FILES; do
  BASENAME=$(basename "$FILE" .cpp)
  if grep -q "int main" "$FILE"; then
    echo "🔧 Adding $BASENAME to CMakeLists.txt..."
    echo "add_executable($BASENAME src/$BASENAME.cpp)" >> "$CMAKE_FILE"
    NECESSARY_EXECUTABLES+=("$BASENAME")
  fi
done

if [ ${#NECESSARY_EXECUTABLES[@]} -eq 0 ]; then
  echo "❌ No .cpp files with a main function found. Restoring original CMakeLists.txt..."
  mv "${CMAKE_FILE}.bak" "$CMAKE_FILE"
  exit 1
fi

# Run cmake in the parent directory
echo -e "\n🚀 Running cmake in the parent directory..."
cmake ..
if [ $? -ne 0 ]; then
  echo "❌ Error: cmake failed. Restoring original CMakeLists.txt..."
  mv "${CMAKE_FILE}.bak" "$CMAKE_FILE"
  exit 1
else
  echo "✅ CMake completed successfully. Cleaning up backup..."
  rm -f "${CMAKE_FILE}.bak"
fi

# Run make in the current directory
echo -e "\n🔨 Building with make..."
make
if [ $? -ne 0 ]; then
  echo "❌ Error: make failed."
  exit 1
fi

# Find all executables in the current directory
EXECUTABLES=$(find . -maxdepth 1 -type f -executable)

# Check if any executables are found
if [ -z "$EXECUTABLES" ]; then
  echo -e "\n❌ Error: No executables found in the current directory."
  exit 1
fi

# Use fzf to allow the user to select an executable
echo -e "\n✨ Build completed! Select an executable to run:"
EXECUTABLE=$(echo "$EXECUTABLES" | fzf --prompt="Select executable: ")

# Check if a selection was made
if [ -z "$EXECUTABLE" ]; then
  echo -e "\n👋 Exiting. No executable selected."
  exit 0
fi

# Run the selected executable
echo -e "\n✅ You selected: $EXECUTABLE"
echo -e "\n🚀 Running $EXECUTABLE..."
$EXECUTABLE
