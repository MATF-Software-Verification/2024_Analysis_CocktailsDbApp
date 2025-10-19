#!/bin/bash

# Espresso - Instrumented UI Tests with JaCoCo Coverage
# Runs Android instrumented tests and generates code coverage reports

set -e

echo "Running Espresso Instrumented Tests with JaCoCo Coverage..."
echo ""

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
RESULTS_DIR="$SCRIPT_DIR/results"
PROJECT_PATH="$(cd "$(dirname "${BASH_SOURCE[0]}")/../CocktailsDbApp" && pwd)"

rm -rf "$RESULTS_DIR"
mkdir -p "$RESULTS_DIR"

echo "Project: CocktailsDbApp"
echo "Results: espresso/results/"
echo ""

cd "$PROJECT_PATH"

echo ""
echo "Checking for connected device/emulator..."

if adb devices | grep -q "device$"; then
    echo "Device/Emulator detected"
    
    echo ""
    echo "Running instrumented tests (Espresso)..."
    
    ./gradlew connectedDebugAndroidTest createDebugAndroidTestCoverageReport --rerun-tasks --no-daemon > "$RESULTS_DIR/espresso-output.log" 2>&1 || true
    
    # Copy instrumented test reports
    echo ""
    echo "Collecting test reports and coverage..."
    
    if [ -d "app/build/reports/androidTests/connected" ]; then
        cp -r app/build/reports/androidTests/connected/* "$RESULTS_DIR/" 2>/dev/null || true
    fi
    
    if [ -d "app/build/outputs/androidTest-results/connected" ]; then
        cp -r app/build/outputs/androidTest-results/connected/* "$RESULTS_DIR/" 2>/dev/null || true
    fi
    
    # Copy JaCoCo coverage reports (instrumented tests only)
    if [ -d "app/build/reports/coverage/androidTest/debug" ]; then
        cp -r app/build/reports/coverage/androidTest/debug "$RESULTS_DIR/jacoco" 2>/dev/null || true
    fi
    
else
    echo "ERROR: No Android device/emulator connected"
    echo ""
    echo "To run Espresso tests, you need:"
    echo "   1. Start an Android emulator: 'emulator -avd <AVD_NAME>'"
    echo "   2. Or connect a physical device via USB"
    echo "   3. Run this script again"
    echo ""
    echo "To check connected devices: 'adb devices'"
    
    exit 1
fi

echo ""
echo "Espresso tests completed"
echo "Reports generated:"
echo "   - $RESULTS_DIR/debug/index.html"
echo "   - $RESULTS_DIR/jacoco/connected/index.html"
