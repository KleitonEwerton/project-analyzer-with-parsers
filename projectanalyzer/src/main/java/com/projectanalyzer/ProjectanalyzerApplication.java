package com.projectanalyzer;

import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjectanalyzerApplication {

	public static void main(String[] args) {

		try {

			GitHub github = new GitHubBuilder().build();

			// // Obtenha informações sobre um usuário
			GHUser user = github.getUser("KleitonEwerton");
			// System.out.println("Nome: " + user.getName());
			// System.out.println("Localização: " + user.getLocation());

			// // Liste os repositórios do usuário
			// for (GHRepository repo : user.listRepositories()) {
			// System.out.println(repo.getName());
			// repo.listLanguages().forEach((k, v) -> {
			// System.out.println(k + " : " + v);
			// });
			// }

		} catch (Exception e) {

		}

		SpringApplication.run(ProjectanalyzerApplication.class, args);
	}

}
