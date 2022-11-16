# Only focus on changes for the current DIFF from the active PR
# github.dismiss_out_of_range_messages

# Identify if a PR is not yet meant to be merged, by commenting with a warning
has_wip_label = github.pr_labels.any? { |label| label.include? "Engineers at work" }
has_wip_title = github.pr_title.include? "[WIP]"
if has_wip_label || has_wip_title
	warn("PR is marked as Work in Progress")
end

# Warn when there is a big PR
warn("Big PR") if git.lines_of_code > 5000

File.open("settings.gradle", "r") do |file_handle|
  file_handle.each_line do |setting|
    if setting.include? "include"
        gradleModule = setting[10, setting.length-12]

        androidLintDebugFile = String.new(gradleModule+"/build/reports/lint-results-debug.xml")
        if File.file?(androidLintDebugFile)
            android_lint.skip_gradle_task = true
            android_lint.severity = "Warning"
            android_lint.report_file = androidLintDebugFile
            android_lint.filtering = true
            android_lint.lint(inline_mode: true)
        else
            warn("No lint report found")
        end

        detektFile = String.new(gradleModule+"/build/reports/detekt/detekt.xml")
        if File.file?(detektFile)
            kotlin_detekt.report_file = detektFile
            kotlin_detekt.skip_gradle_task = true
            kotlin_detekt.severity = "warning"
            kotlin_detekt.filtering = true
            kotlin_detekt.detekt(inline_mode: true)
        else
            warn("No Detekt report found")
        end
    end
  end
end
