# 2024_Analysis_CocktailsDbApp

## Project Description  
Comprehensive software verification analysis of **CocktailsDbApp** - an Android application for cocktail enthusiasts.

- **GitHub URL**: [https://github.com/stefan-hlw/CocktailsDbApp](https://github.com/stefan-hlw/CocktailsDbApp)  
- **Branch analyzed**: `main`  
- **Commit hash**: `3ceb0fcb8135c4ce81de034c13dad6109f583237`  
- **Analysis Date**: October 2025

### Application Overview

The project is a Kotlin-based Android application designed for cocktail enthusiasts. It provides a 
curated collection of recipes for both alcoholic and non-alcoholic drinks. Users can create 
personalized profiles and save their favorite cocktails for easy access, enhancing their mixology 
experience.

### Analysis Highlights

- **Total Tests**: 140 (101 unit + 39 UI)
- **Pass Rate**: 100%
- **Unit Test Coverage**: 11.8% overall (100% on Model & Repository layers)
- **Static Analysis**: 8 code smells 

## Tools and Techniques Used

### 1. **Unit Testing (JUnit + Kover)**
- **Tests**: 101 unit tests
- **Framework**: JUnit 5, MockK, Kotlin Coroutines Test
- **Coverage Tool**: Kover (Kotlin-native code coverage)
- **Scope**: 
  - Model layer (40 tests) - 100% coverage
  - Repository layer (20 tests) - 100% coverage
  - ViewModel layer (41 tests) - Business logic validation - 100% coverage
- **Script**: `junit/run_tests.sh`

### 2. **UI Testing (Espresso + JaCoCo)**
- **Tests**: 39 instrumented tests
- **Framework**: Espresso, AndroidX Test, UIAutomator
- **Coverage Tool**: JaCoCo
- **Scope**:
  - Database tests (4 tests) - Room persistence validation
  - Authentication flows (22 tests) - Login & registration
  - Main functionality (13 tests) - Cocktail browsing, favorites, search
- **Requires**: Connected Android device or emulator (API 29+)
- **Script**: `espresso/run_espresso.sh`

### 3. **Static Code Analysis (detekt)**
- **Purpose**: Code quality, complexity, and code smell detection
- **Findings**: Only 8 issues across 85 Kotlin files
- **Script**: `detekt/run_detekt.sh`

### 4. **Code Style Analysis (ktlint)**
- **Purpose**: Kotlin code style and formatting enforcement
- **Issues Found**: 767 style violations (all auto-fixable)
- **Scope**: Source code only (auto-generated files excluded)
- **Impact**: No functional issues, purely stylistic
- **Script**: `ktlint/run_ktlint.sh`

## Project Structure

```
2024_Analysis_CocktailsDbApp/
├── CocktailsDbApp/              # Original Android project (git submodule)
├── ktlint/                      # Code style checker
│   ├── run_ktlint.sh           # Script to run ktlint
│   └── results/                # ktlint analysis results
├── detekt/                      # Static code analysis
│   ├── run_detekt.sh           # Script to run detekt
│   ├── detekt-config.yml       # Detekt configuration
│   └── results/                # detekt analysis results
├── junit/                       # JUnit unit tests + Kover coverage
│   ├── run_tests.sh            # Script to run unit tests with coverage
│   └── results/                # Test reports and Kover coverage
├── espresso/                    # Espresso instrumented tests + JaCoCo coverage
│   ├── run_espresso.sh         # Script to run Espresso tests with coverage
│   └── results/                # Espresso test reports and JaCoCo coverage
├── .github/                    # CI/CD configuration
│   └── workflows/              # GitHub Actions workflows
├── ProjectAnalysisReport.md   # Detailed analysis findings
├── README.md                  
└── .gitmodules               # Git submodule configuration
```

## Setup Instructions

### Prerequisites
- Java 17 or higher
- Android Studio or Android SDK
- Gradle
- Homebrew (macOS) or package manager


### Installation Steps

1. **Clone and initialize:**
   ```bash
   git clone <repository-url>
   cd 2024_Analysis_CocktailsDbApp
   git submodule update --init --recursive
   ```

2. **Install required tools:**
   ```bash
   # Install ktlint and detekt
   brew install ktlint detekt
   
   # Ensure Java 17+ and Android SDK are installed
   ```

3. **Run analysis tools:**
   ```bash
   bash ktlint/run_ktlint.sh           # Code style analysis
   bash detekt/run_detekt.sh           # Static code analysis
   bash junit/run_tests.sh             # Unit tests with Kover coverage
   bash espresso/run_espresso.sh       # Instrumented tests with JaCoCo coverage
   ```

## Analysis Reports

### Comprehensive Analysis Document
📄 **`ProjectAnalysisReport.md`** - Complete testing and code quality analysis

### Tool-Specific Reports

All tool reports are in their respective `results/` directories:

#### **Unit Tests** (`junit/results/`)
- `index.html` - Test execution results 
- `coverage/index.html` - Kover coverage report

#### **UI Tests** (`espresso/results/`)
- `debug/index.html` - Espresso test results 
- `coverage/androidTest/debug/connected/index.html` - JaCoCo UI coverage


#### **Static Analysis** (`detekt/results/`)
- `detekt_report.html` - Interactive HTML report
- `detekt_report.md` - Markdown summary

#### **Code Style** (`ktlint/results/`)
- `ktlint_report.txt` - Text report (767 issues)
- `ktlint_report.html` - HTML report with violation details


**Author:** Dragana Zdravkovic (mi231020)  
**Contact:** dragana.zdravkovic602@gmail.com  
**Project:** CocktailsDbApp Software Verification Analysis  
**Date:** October 2025  
**University:** Faculty of Mathematics, University of Belgrade
