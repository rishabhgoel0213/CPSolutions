#!/bin/bash

# Set the directory to clean (current directory by default)
OUT_DIR="."

# List of CMake and Make generated files/directories to clean
GENERATED_FILES=(
  "CMakeFiles"
  "CMakeCache.txt"
  "Makefile"
  "*.cmake"
  "*.o"
  "*.a"
  "*.so"
  "*.out"
  "*.log"
)
GENERATED_EXECUTABLES=$(find "$OUT_DIR" -maxdepth 1 -type f -executable)

# Function to clean generated files
clean_generated_files() {
  echo "🧹 Cleaning generated files in $OUT_DIR..."

  # Remove each type of generated file
  for PATTERN in "${GENERATED_FILES[@]}"; do
    find "$OUT_DIR" -maxdepth 1 -name "$PATTERN" -exec rm -rf {} \;
  done

  # Remove generated executables
  for EXECUTABLE in $GENERATED_EXECUTABLES; do
    echo "   🗑️ Removing $EXECUTABLE"
    rm -f "$EXECUTABLE"
  done

  echo "✅ Clean complete! Non-generated files are preserved."
}

# Prompt user for confirmation
read -p "Are you sure you want to clean the generated files in $OUT_DIR? [y/N]: " CONFIRM
if [[ "$CONFIRM" =~ ^[Yy]$ ]]; then
  clean_generated_files
else
  echo "❌ Clean operation cancelled."
fi
