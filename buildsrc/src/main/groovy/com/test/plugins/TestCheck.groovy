package com.test.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project

class TestCheck implements Plugin<Project> {

    @Override
    void apply(Project project) {
        System.out.println("hello world  <<<<<<<<<<<<<<<<<<<")
    }
}
