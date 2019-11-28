package steps

class Checkout implements Serializable {

    def steps

    Checkout(steps) { this.steps = steps }

    def checkout(args) {
        steps.sh "${steps.tool 'Git'}/bin/git clone https://github.com/sachingorade/${args}"
    }

}