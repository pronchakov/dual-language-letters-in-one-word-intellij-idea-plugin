package com.github.pronchakov.duallanguagelettersinonewordintellijideaplugin.services

import com.intellij.openapi.project.Project
import com.github.pronchakov.duallanguagelettersinonewordintellijideaplugin.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
