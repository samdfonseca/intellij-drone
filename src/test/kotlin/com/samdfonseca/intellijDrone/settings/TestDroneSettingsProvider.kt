package com.samdfonseca.intellijDrone.settings

import com.intellij.openapi.application.PathManager
import com.intellij.testFramework.UsefulTestCase
import com.intellij.testFramework.LightPlatformTestCase
import com.intellij.testFramework.builders.JavaModuleFixtureBuilder
import com.intellij.testFramework.fixtures.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.InjectMockKs
import org.junit.*


public class TestDroneSettingsProvider : LightPlatformTestCase() {
    @MockK
    lateinit var storedSecrets: SecretsStorage

    override fun getTestDirectoryName(): String = "testdata"

}
